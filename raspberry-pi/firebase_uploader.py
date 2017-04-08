import json
import uuid
import os
import random
from os import listdir
from os.path import isfile, join

import pyrebase

image_extensions = ['jpg']

configFile = open('firebase-config.json', 'r+')
config = json.load(configFile)

firebase = pyrebase.initialize_app(config)

db = firebase.database()

storage = firebase.storage()

this_shower_id = "shower_1"

showerRef = db.child("showers").child(this_shower_id)
showerRef.child("is_occupied").set(True)
