from jinja2 import Environment, FileSystemLoader
import os

# --- 1. Sample Data (Replace with your actual data sources) ---
vvv_data_main = {
  "Total Scans": 1500,
  "Successful Scans": 1450,
  "Failed Scans": 50,
  "Pending": 0
}

vvv_data_processed = {
  "Images Processed": 1450,
  "Average Processing Time (s)": 2.5,
  "Peak Load": "85%"
}

vvv_status_counts = {
  "Status OK": 1400,
  "Status Warning": 40,
  "Status Error": 10
}

ccc_data_main = {
  "Total Records": 25000,
  "Records Analyzed": 24800,
  "Anomalies Found": 120
}

ccc_data_processed = {
  "Time Taken (min)": 45,
  "Records per Second": 9.18, # 24800 / (45*60)
  "Accuracy": "99.5%"
}

ccc_status_counts = {
  "Type A Anomaly": 70,
  "Type B Anomaly": 30,
  "Type C Anomaly": 20
}

file_loader = FileSystemLoader('.')
env = Environment(loader=file_loader)

template_file_name = "auto-email-template.html"
template = env.get_template(template_file_name)

context = {
  "VVV_main_data": vvv_data_main,
  "VVV_processed_data": vvv_data_processed,
  "VVV_main_specific_status_count": vvv_status_counts,
  "CCC_main_data": ccc_data_main,
  "CCC_processed_data": ccc_data_processed,
  "CCC_main_specific_status_count": ccc_status_counts,
}

rendered_html = template.render(context)

print(rendered_html)

output_file_name = "rendered_report.html"
with open(output_file_name, "w", encoding="utf-8") as f:
  f.write(rendered_html)

print(f"\nReport successfully generated and saved to {output_file_name}")