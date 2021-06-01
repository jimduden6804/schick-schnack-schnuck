IMAGE_NAME := schick-schnack-schnuck
PORT ?= 4242

build:
	lein deps
	lein uberjar
	docker build -t $(IMAGE_NAME) .

run:
	docker run -p 8080:4242 $(IMAGE_NAME)
