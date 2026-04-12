import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
MESSAGES_FILE_PATH = "files/messages.json"
APP_STATE_FILE_PATH = "files/app_state.json"
ACTION_VALUE = "send_message"
PAGE_VALUE = "sendmessages"
MESSAGE_VALUE = "How long will it take to arrive?"


def validate_task_thirty(result=None, device_id=None, backup_dir=None):
    message_file_path = os.path.join(backup_dir, "messages.json") if backup_dir else "messages.json"
    app_state_file_path = os.path.join(backup_dir, "app_state.json") if backup_dir else "app_state.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", MESSAGES_FILE_PATH])
    subprocess.run(cmd, stdout=open(message_file_path, "w"))

    state_cmd = ["adb"]
    if device_id:
        state_cmd.extend(["-s", device_id])
    state_cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", APP_STATE_FILE_PATH])
    subprocess.run(state_cmd, stdout=open(app_state_file_path, "w"))

    try:
        with open(message_file_path, "r", encoding="utf-8") as file:
            data = json.load(file)
            events = data if isinstance(data, list) else [data]
        with open(app_state_file_path, "r", encoding="utf-8") as file:
            state = json.load(file)
    except Exception:
        return False

    orders = state.get("orders", [])
    if not orders:
        return False
    latest_order = orders[0]
    latest_order_id = latest_order.get("id")
    latest_merchant = latest_order.get("merchantName")

    for event in reversed(events):
        if event.get("action") != ACTION_VALUE or event.get("page") != PAGE_VALUE:
            continue
        extra_data = event.get("extra_data", {})
        if (
            str(extra_data.get("message", "")).strip() == MESSAGE_VALUE
            and extra_data.get("order_id") == latest_order_id
            and extra_data.get("merchant_name") == latest_merchant
        ):
            return True

    return False


if __name__ == "__main__":
    print(validate_task_thirty())
