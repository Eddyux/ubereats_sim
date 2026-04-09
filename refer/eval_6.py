import subprocess
import json
import os


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "request_ride"
PAGE_VALUE = "choose_ride"


def validate_task_six(result=None, device_id=None, backup_dir=None):
    message_file_path = os.path.join(backup_dir, "messages.json") if backup_dir else "messages.json"

    cmd = ['adb']
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", DEVICE_FILE_PATH])
    subprocess.run(cmd, stdout=open(message_file_path, "w"))

    try:
        with open(message_file_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            events = data if isinstance(data, list) else [data]
    except:
        return False

    for event in reversed(events):
        if event.get("action") != ACTION_VALUE:
            continue
        if event.get("page") != PAGE_VALUE:
            continue

        extra_data = event.get("extra_data", {})
        if (
            str(extra_data.get("pickup_location", "")).strip().lower() == "jianghanlu"
            and str(extra_data.get("dropoff_location", "")).strip().lower() == "jiedaokou"
            and extra_data.get("selected_ride") == "Share"
            and extra_data.get("is_cheapest") is True
        ):
            return True
    return False


if __name__ == "__main__":
    print(validate_task_six())
