import json
import uuid
import os
import random
from os import listdir
from os.path import isfile, join

import pyrebase

import RPi.GPIO as GPIO          #Import GPIO library
import time                      #Import time library


configFile = open('firebase-config.json', 'r+')
config = json.load(configFile)

firebase = pyrebase.initialize_app(config)

db = firebase.database()

# storage = firebase.storage()

this_shower_id = "shower_1"

showerRef = db.child("showers").child(this_shower_id)
occupiedRef = showerRef.child("is_occupied")

GPIO.setmode(GPIO.BOARD)         #Set GPIO pin numbering
GPIO.setup(12, GPIO.IN, pull_up_down=GPIO.PUD_UP) #Enable input and pull up resistors


is_occupied = False

while True:
    button_input_state = GPIO.input(12) #Read and store value of input to a variable
    is_pressed = not button_input_state
    if is_pressed != is_occupied:
        is_occupied = is_pressed
        db.child("showers").child(this_shower_id).child("is_occupied").set(is_occupied)
        print('State Switched')   #Print 'Button Pressed'
        print(is_occupied)
        time.sleep(0.3)           #Delay of 0.3s
