import subprocess
import json
import os


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "confirm_payment"
PAGE_VALUE = "payment"
MERCHANT_NAME = "McDonald's"
REQUIRED_ITEMS = {"Hash Browns", "Double Cheeseburger"}


def validate_task_eight(result=None, device_id=None, backup_dir=None):
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
        item_names = set(extra_data.get("item_names", []))
        if (
            extra_data.get("merchant_name") == MERCHANT_NAME
            and REQUIRED_ITEMS.issubset(item_names)
            and extra_data.get("delivery_mode") == "Schedule"
            and extra_data.get("scheduled_for") == "Tomorrow 12:00 PM"
        ):
            return True
    return False


if __name__ == "__main__":
    print(validate_task_eight())
