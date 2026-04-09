import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "send_message"
PAGE_VALUE = "sendmessages"
MESSAGE_VALUE = "How long will it take to arrive?"


def validate_task_thirty(result=None, device_id=None, backup_dir=None):
    message_file_path = os.path.join(backup_dir, "messages.json") if backup_dir else "messages.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", DEVICE_FILE_PATH])
    subprocess.run(cmd, stdout=open(message_file_path, "w"))

    try:
        with open(message_file_path, "r", encoding="utf-8") as file:
            data = json.load(file)
            events = data if isinstance(data, list) else [data]
    except Exception:
        return False

    for event in reversed(events):
        if event.get("action") != ACTION_VALUE or event.get("page") != PAGE_VALUE:
            continue
        extra_data = event.get("extra_data", {})
        if str(extra_data.get("message", "")).strip() == MESSAGE_VALUE:
            return True

    return False


if __name__ == "__main__":
    print(validate_task_thirty())
