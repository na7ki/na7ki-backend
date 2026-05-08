import boto3
import os
from botocore.client import Config

# ==========================================
# Configuration
# ==========================================
MINIO_ENDPOINT = 'http://localhost:9000'
ACCESS_KEY = 'minioadmin'
SECRET_KEY = 'minioadmin'
BUCKET_NAME = 'packages-images' 

# Replace these with the actual names of the folders you uploaded
folders_to_scan = ['images/singular/', 'images/plural/'] 

# ==========================================
# Initialization
# ==========================================
s3 = boto3.client(
    's3',
    endpoint_url=MINIO_ENDPOINT,
    aws_access_key_id=ACCESS_KEY,
    aws_secret_access_key=SECRET_KEY,
    config=Config(signature_version='s3v4'),
    region_name='us-east-1'
)

sql_statements = []

# Using a paginator ensures we get all files even if a folder has > 1000 images
paginator = s3.get_paginator('list_objects_v2')

print("Scanning MinIO and generating SQL statements...")

for folder in folders_to_scan:
    pages = paginator.paginate(Bucket=BUCKET_NAME, Prefix=folder)
    
    for page in pages:
        if 'Contents' not in page:
            print(f"  -> No files found in {folder}")
            continue

        for obj in page['Contents']:
            file_key = obj['Key']

            # Skip the directory object itself if it exists
            if file_key.endswith('/'):
                continue

            # 1. Extract filename (e.g., 'package_1/apple.png' -> 'apple.png')
            filename = os.path.basename(file_key)

            # 2. Extract label (e.g., 'apple.png' -> 'apple')
            label = os.path.splitext(filename)[0]

            # 3. Construct the public URL
            image_url = f"{MINIO_ENDPOINT}/{BUCKET_NAME}/{file_key}"

            # 4. Define the type (Using the folder name, e.g., 'package_1')
            image_type = folder.strip('/')

            # Escape any single quotes in the label to prevent SQL syntax errors
            label_escaped = label.replace("'", "''")

            # 5. Create the SQL Insert statement
            # Spring Boot's GenerationType.IDENTITY means the DB handles the 'id'
            sql = f"INSERT INTO images (label, image_url, type) VALUES ('{label_escaped}', '{image_url}', '{image_type}');"
            sql_statements.append(sql)

# ==========================================
# Output Generation
# ==========================================
output_filename = 'insert_images.sql'

with open(output_filename, 'w', encoding='utf-8') as f:
    # Add a standard SQL header
    f.write("-- Auto-generated MinIO Image Inserts\n")
    f.write("BEGIN;\n\n")
    
    for statement in sql_statements:
        f.write(statement + '\n')
        
    f.write("\nCOMMIT;\n")

print(f"Success! Generated {len(sql_statements)} insert statements in '{output_filename}'.")