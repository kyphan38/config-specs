#!/bin/bash

# Generate subtitles using FFmpeg and Whisper
# Usage: ./generate_sub.sh input_file
# Supports: .mp4, .mkv, .avi, .mov, .wav

INPUT_PATH="$1"

if [ -z "$INPUT_PATH" ]; then
  echo "Usage: $0 input_file"
  exit 1
fi

FILENAME=$(basename "$INPUT_PATH")
FILENAME_NO_EXT="${FILENAME%.*}"
EXTENSION="${FILENAME##*.}"
OUTPUT_DIR="./subs"

echo "Generating subtitles for $INPUT_PATH"
echo "Output in $OUTPUT_DIR"

mkdir -p "$OUTPUT_DIR"

if [ "$EXTENSION" = "wav" ]; then
  INPUT_AUDIO="$INPUT_PATH"
else
  TEMP_WAV="$OUTPUT_DIR/temp_audio.wav"
  ffmpeg -i "$INPUT_PATH" -ar 16000 -ac 1 -vn -f wav "$TEMP_WAV" || {
    echo "Failed to extract audio from $INPUT_PATH"
    exit 1
  }
  INPUT_AUDIO="$TEMP_WAV"
fi

whisper --model base.en --max_line_width 65 \
  --max_line_count 1 --word_timestamps True \
  --output_format srt \
  --output_dir "$OUTPUT_DIR" "$INPUT_AUDIO"

if [ $? -eq 0 ]; then
  if [ -f "$OUTPUT_DIR/$(basename "$INPUT_AUDIO" .wav).srt" ]; then
      mv "$OUTPUT_DIR/$(basename "$INPUT_AUDIO" .wav).srt" "$OUTPUT_DIR/$FILENAME_NO_EXT.srt"
      echo "Subtitles saved to $OUTPUT_DIR/$FILENAME_NO_EXT.srt"
  else
      echo "Error: Expected output file not found."
      exit 1
  fi
else
  echo "Failed to generate subtitles (whisper command failed)"
  exit 1
fi

if [ "$EXTENSION" != "wav" ] && [ -f "$TEMP_WAV" ]; then
  rm "$TEMP_WAV"
fi