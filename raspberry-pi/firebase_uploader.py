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

to_push={"YO":"ABCDEFG"}

pushRef = db.child("test")
pushRef.set(to_push)
