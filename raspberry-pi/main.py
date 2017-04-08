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
GPIO.setup(32, GPIO.IN, pull_up_down=GPIO.PUD_UP) #Enable input and pull up resistors
GPIO.setup(12,GPIO.OUT)
GPIO.output(12,GPIO.LOW)

is_occupied = False
get_out = False

while True:
    button_input_state = GPIO.input(32) #Read and store value of input to a variable
    is_pressed = not button_input_state
    if is_pressed != is_occupied:
        is_occupied = is_pressed
        firebase.database().child("showers").child(this_shower_id).child("is_occupied").set(is_occupied)
        if (not is_occupied):
            firebase.database().child("showers").child(this_shower_id).child("get_out").set(False)
        print('Occupied State Switched')
        print(is_occupied)
        time.sleep(0.3)           #Delay of 0.3s
    get_out_result = firebase.database().child("showers").child(this_shower_id).child("get_out").get()
    if get_out_result.val() != get_out:
        get_out = get_out_result.val()
        print('Get Out State Switched')
        print(get_out)
        if get_out:
            GPIO.output(12,GPIO.HIGH)
        else:
            GPIO.output(12,GPIO.LOW)


        
