import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"


def read_json_from_device(device_id=None, package_name=PACKAGE_NAME, file_path=DEVICE_FILE_PATH, backup_dir=None):
    output_path = os.path.join(backup_dir, os.path.basename(file_path)) if backup_dir else os.path.basename(file_path)
    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", package_name, "cat", file_path])

    with open(output_path, "w", encoding="utf-8") as file:
        subprocess.run(cmd, stdout=file, stderr=subprocess.PIPE, check=True, text=True)

    with open(output_path, "r", encoding="utf-8") as file:
        return json.load(file)


def validate_task_eight(result=None, device_id=None, backup_dir=None):
    try:
        all_data = read_json_from_device(device_id=device_id, backup_dir=backup_dir)
        events = all_data if isinstance(all_data, list) else [all_data]
    except Exception:
        return False

    for event in reversed(events):
        if event.get("action") != "confirm_payment" or event.get("page") != "payment":
            continue
        extra_data = event.get("extra_data", {})
        item_names = set(extra_data.get("item_names", []))
        if (
            extra_data.get("merchant_name") == "McDonald's"
            and {"Hash Browns", "Double Cheeseburger"}.issubset(item_names)
            and extra_data.get("delivery_mode") == "Schedule"
            and extra_data.get("scheduled_for") == "Tomorrow 12:00 PM"
        ):
            return True
    return False


if __name__ == "__main__":
    print(validate_task_eight())
