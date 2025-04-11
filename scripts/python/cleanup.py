import os

for filename in os.listdir('.'):
  if '--[TutFlix.ORG]--' in filename:
    new_filename = filename.replace('--[TutFlix.ORG]--', '')
    os.rename(filename, new_filename)
    print(f'Renamed: {filename} -> {new_filename}')