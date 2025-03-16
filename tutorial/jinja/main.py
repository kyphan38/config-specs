from flask import Flask 
from flask import render_template

app = Flask(__name__)

@app.route("/")
def index():
  first_name = "Andy"
  stuff = "This is <strong>Bold</strong> Text"

  favorite_pizza = ["Pepperoni", "Cheese", "Mushrooms", 41]
  return render_template("report.html",
    first_name=first_name,
    stuff=stuff,
    favorite_pizza=favorite_pizza)

@app.route("/user/<name>")
def user(name):
  return render_template("report.html", user_name=name)

if __name__ == "__main__":
  app.run(debug=True)
