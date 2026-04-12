import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ORDER_ACTION = "confirm_payment"
ORDER_PAGE = "payment"
RIDE_ACTION = "request_ride"
RIDE_PAGE = "choose_ride"
REQUIRED_ITEMS = {
    ("Benvenuto Cafe", "Espresso"),
    ("Yunnan Rice Noodle", "Cold Noodle Salad"),
}


def validate_task_thirty_three(result=None, device_id=None, backup_dir=None):
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
    ride_completed = False

    for event in reversed(events):
        action = event.get("action")
        page = event.get("page")
        extra_data = event.get("extra_data", {})

        if action == ORDER_ACTION and page == ORDER_PAGE:
            merchant_name = str(extra_data.get("merchant_name", "")).strip()
            item_names = extra_data.get("item_names", [])
            if isinstance(item_names, list):
                for item_name in item_names:
                    pair = (merchant_name, str(item_name).strip())
                    if pair in REQUIRED_ITEMS:
                        matched_items.add(pair)

        if action == RIDE_ACTION and page == RIDE_PAGE:
            pickup = str(extra_data.get("pickup_location", "")).strip().lower()
            dropoff = str(extra_data.get("dropoff_location", "")).strip().lower()
            if pickup == "jiedaokou" and dropoff == "jianghanlu":
                ride_completed = True

        if ride_completed and matched_items.issuperset(REQUIRED_ITEMS):
            return True

    return False


if __name__ == "__main__":
    print(validate_task_thirty_three())
