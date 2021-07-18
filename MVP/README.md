# MVP
This directory contains a Mac-specific MVP of the concept.

## Record
A [script](record) that periodically takes screenshots while it runs.
```
watch -n 30 'screencapture -px; mv ~/Desktop/Screen\ Shot\ * ~/recordings/'
```
To use it:
```
./record
```

## OCR
A script to run OCR on all the screenshots.
To use it:
```
./ocr
```

## Index
A script to index all the extracted words.
To use it:
```
./index
```

## Search
A script to search the indexes.
```
./search
```
