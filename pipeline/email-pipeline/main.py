# import sys
# import json
# from jinja2 import Environment, FileSystemLoader

# def generate_report(expiry15, expiry25):
#     # Set up Jinja2 environment
#     env = Environment(loader=FileSystemLoader('.'))
#     template = env.get_template('pipeline/email-pipeline/report.html')
    
#     # Render the template with data
#     html_content = template.render(
#         expiry15=expiry15,
#         expiry25=expiry25
#     )
    
#     # Write to output file
#     with open('formatted-report.html', 'w') as f:
#         f.write(html_content)

# if __name__ == "__main__":
#     if len(sys.argv) != 3:
#         print("Usage: python main.py <expiry15_json> <expiry25_json>")
#         sys.exit(1)
    
#     # Parse JSON arguments
#     expiry15 = json.loads(sys.argv[1])  # Now a list of dictionaries
#     expiry25 = json.loads(sys.argv[2])  # Now a list of dictionaries
    
#     # Generate the report
#     generate_report(expiry15, expiry25)

