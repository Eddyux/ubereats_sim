import subprocess
import json
import os


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "toggle_favorite"
PAGE_VALUE = "home"
REQUIRED_FAVORITES = {"McDonald's", "VINEYARD", "Burger King"}


def validate_task_twenty_five(result=None, device_id=None, backup_dir=None):
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

    favorite_states = {}
    for event in events:
        if event.get("action") != ACTION_VALUE:
            continue
        if event.get("page") != PAGE_VALUE:
            continue

        extra_data = event.get("extra_data", {})
        merchant_name = str(extra_data.get("merchant_name", "")).strip()
        if merchant_name not in REQUIRED_FAVORITES:
            continue
        favorite_states[merchant_name] = extra_data.get("favorited") is True

    return all(favorite_states.get(merchant_name) is True for merchant_name in REQUIRED_FAVORITES)


if __name__ == "__main__":
    print(validate_task_twenty_five())
