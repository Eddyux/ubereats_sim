import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "confirm_payment"
PAGE_VALUE = "payment"
REQUIRED_ITEMS = {
    ("Benvenuto Cafe", "Espresso"),
    ("Yunnan Rice Noodle", "Cold Noodle Salad"),
}


def validate_task_thirty_two(result=None, device_id=None, backup_dir=None):
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

    matched_items = set()
    for event in reversed(events):
        if event.get("action") != ACTION_VALUE or event.get("page") != PAGE_VALUE:
            continue

        extra_data = event.get("extra_data", {})
        merchant_name = str(extra_data.get("merchant_name", "")).strip()
        item_names = extra_data.get("item_names", [])
        if not isinstance(item_names, list):
            continue

        for item_name in item_names:
            pair = (merchant_name, str(item_name).strip())
            if pair in REQUIRED_ITEMS:
                matched_items.add(pair)
        if matched_items.issuperset(REQUIRED_ITEMS):
            return True

    return False


if __name__ == "__main__":
    print(validate_task_thirty_two())
