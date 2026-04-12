import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "save_place"
PAGE_VALUE = "settings_home_set"


def validate_task_thirty_four(result=None, device_id=None, backup_dir=None):
    message_file_path = os.path.join(backup_dir, "messages.json") if backup_dir else "messages.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", DEVICE_FILE_PATH])
    subprocess.run(cmd, stdout=open(message_file_path, "w"))

    try:
        with open(message_file_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            events = data if isinstance(data, list) else [data]
    except Exception:
        return False

    has_home = False
    has_work = False
    for event in reversed(events):
        if event.get("action") != ACTION_VALUE or event.get("page") != PAGE_VALUE:
            continue

        extra_data = event.get("extra_data", {})
        building_type = str(extra_data.get("building_type", "")).strip().lower()
        location = str(extra_data.get("location", "")).strip().lower()
        address_label = str(extra_data.get("address_label", "")).strip().lower()

        if building_type == "house" and location == "jianghanlu" and address_label == "home":
            has_home = True
        if building_type == "office" and location == "jiedaokou" and address_label == "work":
            has_work = True

        if has_home and has_work:
            return True

    return False


if __name__ == "__main__":
    print(validate_task_thirty_four())
