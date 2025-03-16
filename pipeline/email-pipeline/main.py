# def extract_data(s):
#     # Remove outer brackets
#     s = s[1:-1]
    
#     # Split into individual entries and filter out empty strings
#     parts = [p for p in s.split("[") if "]" in p]
#     res = [p.split("]")[0] for p in parts]
    
#     # Convert each entry into a dictionary
#     result = []
#     for item in res:
#         # Split by comma and create key-value pairs
#         pairs = [pair.strip() for pair in item.split(",")]
#         entry_dict = {}
#         for pair in pairs:
#             key, value = pair.split(":")
#             entry_dict[key] = value
#         result.append(entry_dict)
    
#     return result
  


# if __name__ == "__main__":
#   expiry_within_7 = "[[username:a-db-user-1, expiry:7, env:a], [username:a-db-user-2, expiry:3, env:a], [username:b-db-user-1, expiry:4, env:b], [username:b-db-user-2, expiry:7, env:b], [username:c-db-user-1, expiry:1, env:c], [username:c-db-user-2, expiry:2, env:c]]"
#   expiry_within_7_data = extract_data(expiry_within_7)
#   expiry_within_15 = "[[username:a-db-user-3, expiry:15, env:a], [username:b-db-user-3, expiry:15, env:b], [username:c-db-user-3, expiry:15, env:c]]"
#   expiry_within_15_data = extract_data(expiry_within_15)

#   print(expiry_within_7_data)
  

  # expiry_within_15 = "[[username:a-db-user-3, expiry:15, env:a], [username:b-db-user-3, expiry:15, env:b], [username:c-db-user-3, expiry:15, env:c]]"
  # expiry_within_15_data = extract_data(expiry_within_15)


from jinja2 import Environment, FileSystemLoader
import os

def extract_data(s):
    s = s[1:-1]
    parts = [p for p in s.split("[") if "]" in p]
    res = [p.split("]")[0] for p in parts]
    
    result = []
    for item in res:
        pairs = [pair.strip() for pair in item.split(",")]
        entry_dict = {}
        for pair in pairs:
            key, value = pair.split(":")
            if key == "expiry":
                entry_dict[key] = int(value)  # Convert expiry to integer
            else:
                entry_dict[key] = value
        result.append(entry_dict)
    
    return result

def generate_html_report(template_path, output_path, expiry_within_7_data, expiry_within_15_data):

    # Set up Jinja2 environment
    env = Environment(loader=FileSystemLoader(os.path.dirname(template_path)))
    template = env.get_template(os.path.basename(template_path))
    
    # Render the template with data
    html_content = template.render(
        expiry_within_7=expiry_within_7_data,
        expiry_within_15=expiry_within_15_data
    )
    
    # Write to output file
    with open(output_path, 'w') as f:
        f.write(html_content)
    
    return html_content

if __name__ == "__main__":
    expiry_within_7 = "[[username:a-db-user-1, expiry:7, env:a], [username:a-db-user-2, expiry:3, env:a], [username:b-db-user-1, expiry:4, env:b], [username:b-db-user-2, expiry:7, env:b], [username:c-db-user-1, expiry:1, env:c], [username:c-db-user-2, expiry:2, env:c]]"
    expiry_within_15 = "[[username:a-db-user-3, expiry:15, env:a], [username:b-db-user-3, expiry:15, env:b], [username:c-db-user-3, expiry:15, env:c]]"
    
    # Extract data
    expiry_within_7_data = extract_data(expiry_within_7)
    expiry_within_15_data = extract_data(expiry_within_15)
    
    # Generate HTML report
    template_path = "./templates/email-report.html"
    output_path = "report.html"
    html_content = generate_html_report(template_path, output_path, expiry_within_7_data, expiry_within_15_data)
    
    print("Report generated successfully at:", output_path)