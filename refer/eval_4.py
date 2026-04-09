import subprocess
import json
import os


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/app_state.json"


def validate_task_four(result=None, device_id=None, backup_dir=None):
    message_file_path = os.path.join(backup_dir, "app_state.json") if backup_dir else "app_state.json"

    cmd = ['adb']
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", DEVICE_FILE_PATH])
    subprocess.run(cmd, stdout=open(message_file_path, "w"))

    try:
        with open(message_file_path, "r", encoding="utf-8") as f:
            data = json.load(f)
    except:
        return False

    cart_items = data.get("cartItems", [])
    return isinstance(cart_items, list) and len(cart_items) == 0


if __name__ == "__main__":
    print(validate_task_four())
