import ast
import re

def extract_data(data):
  data = data[1:-1]
  data = data.split(",")

  for i in data:
    print(i)
    print("\n")

  print(data)

  return 0
  


if __name__ == "__main__":
  expiry_within_7 = "[[username:a-db-user-1, expiry:7, env:a], [username:a-db-user-2, expiry:3, env:a], [username:b-db-user-1, expiry:4, env:b], [username:b-db-user-2, expiry:7, env:b], [username:c-db-user-1, expiry:1, env:c], [username:c-db-user-2, expiry:2, env:c]]"
  expiry_within_7_data = extract_data(expiry_within_7)

  # expiry_within_15 = "[[username:a-db-user-3, expiry:15, env:a], [username:b-db-user-3, expiry:15, env:b], [username:c-db-user-3, expiry:15, env:c]]"
  # expiry_within_15_data = extract_data(expiry_within_15)
  print(expiry_within_7_data)
  