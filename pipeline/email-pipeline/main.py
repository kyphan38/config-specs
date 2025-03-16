import ast

def extract_data(data):
  # First, replace the colon-separated items with key-value pairs in dictionary format
  data = data.replace('username:', '"username":').replace('expiry:', '"expiry":').replace('env:', '"env":')
  
  # Convert the string representation of the list to an actual list using ast.literal_eval
  data_list = ast.literal_eval(data)
  
  return data_list


if __name__ == "__main__":
  expiry_within_7 = "[[username:a-db-user-1, expiry:7, env:a], [username:a-db-user-2, expiry:3, env:a], [username:b-db-user-1, expiry:4, env:b], [username:b-db-user-2, expiry:7, env:b], [username:c-db-user-1, expiry:1, env:c], [username:c-db-user-2, expiry:2, env:c]]"
  expiry_within_15 = "[[username:a-db-user-3, expiry:15, env:a], [username:b-db-user-3, expiry:15, env:b], [username:c-db-user-3, expiry:15, env:c]]"

  expiry_within_7_data = extract_data(expiry_within_7)
  expiry_within_15_data = extract_data(expiry_within_15)
  print(expiry_within_7_data)
  