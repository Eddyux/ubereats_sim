import subprocess
import json
import os


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "add_to_cart"
PAGE_VALUE = "product_detail"
REQUIRED_ITEMS = {
    ("McDonald's", "Hash Browns"),
    ("McDonald's", "Double Cheeseburger"),
    ("Burger King", "Whopper"),
}


def validate_task_twenty_two(result=None, device_id=None, backup_dir=None):
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

    matched_items = set()
    for event in events:
        if event.get("action") != ACTION_VALUE:
            continue
        if event.get("page") != PAGE_VALUE:
            continue

        extra_data = event.get("extra_data", {})
        merchant_name = str(extra_data.get("merchant_name", "")).strip()
        item_name = str(extra_data.get("item_name", "")).strip()
        if (merchant_name, item_name) in REQUIRED_ITEMS:
            matched_items.add((merchant_name, item_name))
        if merchant_name == "McDonald's" and item_name == "Hash Brown":
            matched_items.add(("McDonald's", "Hash Browns"))

    return matched_items.issuperset(REQUIRED_ITEMS)


if __name__ == "__main__":
    print(validate_task_twenty_two())
