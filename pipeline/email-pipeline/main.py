from jinja2 import Environment, FileSystemLoader
import os
import sys

def parse_string_to_dicts(string):
  string = string[1:-1]
  
  parts = [part for part in string.split("[") if "]" in part]
  entries = [entry.split("]")[0] for entry in parts]
  
  user_entries = []
  for entry in entries:
    key_value_pairs = [pair.strip() for pair in entry.split(",")]
    user_dict = {}
    for pair in key_value_pairs:
      key, value = pair.split(":")
      user_dict[key] = value
    user_entries.append(user_dict)
  
  return user_entries

def generate_html_report(expiry_within_7_users, expiry_within_15_users, template_path, output_path):
  env = Environment(loader=FileSystemLoader(os.path.dirname(template_path)))
  template = env.get_template(os.path.basename(template_path))
  
  html_content = template.render(
    expiry_within_7=expiry_within_7_users,
    expiry_within_15=expiry_within_15_users
  )
  
  with open(output_path, 'w') as f:
    f.write(html_content)
  
  return html_content

if __name__ == "__main__":
  expiry_within_7_string = sys.argv[1]
  expiry_within_15_string = sys.argv[2]
  template_path = sys.argv[3]
  output_path = sys.argv[4]

  expiry_within_7_users = parse_string_to_dicts(expiry_within_7_string)
  expiry_within_15_users = parse_string_to_dicts(expiry_within_15_string)
  
  template_path = "./templates/raw-email-report.html"
  output_path = "email-report.html"
  html_content = generate_html_report(expiry_within_7_users, expiry_within_15_users, template_path, output_path)
  
  print("Report generated successfully at:", output_path)