import subprocess
import json
import os


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "open_payment"
PAGE_VALUE = "payment"
MERCHANT_NAME = "McDonald's"
ITEM_NAME = "Hash Browns"


def validate_task_two(result=None, device_id=None, backup_dir=None):
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
        item_names = extra_data.get("item_names", [])
        if (
            extra_data.get("merchant_name") == MERCHANT_NAME
            and ITEM_NAME in item_names
            and extra_data.get("total_quantity") == 1
            and extra_data.get("delivery_mode") == "Standard"
            and extra_data.get("scheduled_for") == ""
            and extra_data.get("default_delivery") is True
        ):
            return True
    return False


if __name__ == "__main__":
    print(validate_task_two())
