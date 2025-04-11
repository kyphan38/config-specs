#!/bin/bash

# Generate subtitles using FFmpeg and Whisper
# Usage: ./generate_sub.sh video.mp4

VIDEO_PATH="$1"

if [ -z "$VIDEO_PATH" ]; then
  echo "Usage: $0 video_file.mp4"
  exit 1
fi

FILENAME=$(basename "$VIDEO_PATH")
FILENAME_NO_EXT="${FILENAME%.*}"
OUTPUT_DIR="./subs"

echo "Generating subtitles for $VIDEO_PATH"
echo "Output in $OUTPUT_DIR"

mkdir -p "$OUTPUT_DIR"

ffmpeg -i "$VIDEO_PATH" -ar 16000 -ac 1 -vn -f wav - | \
whisper --model base.en --max_line_width 65 \
  --max_line_count 1 --word_timestamps True \
  --output_format srt \
  --output_dir "$OUTPUT_DIR" -

if [ $? -eq 0 ]; then
  if [ -f "$OUTPUT_DIR/-.srt" ]; then
      mv "$OUTPUT_DIR/-.srt" "$OUTPUT_DIR/$FILENAME_NO_EXT.srt"
      echo "Subtitles saved to $OUTPUT_DIR/$FILENAME_NO_EXT.srt"
  else
      echo "Error: Expected output file $OUTPUT_DIR/-.srt not found."
      exit 1
  fi
else
  echo "Failed to generate subtitles (whisper command failed)"
  exit 1
fi