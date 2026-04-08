import json
import os
import subprocess


PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/app_state.json"


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


def validate_task_four(result=None, device_id=None, backup_dir=None):
    try:
        state = read_json_from_device(device_id=device_id, backup_dir=backup_dir)
    except Exception:
        return False

    cart_items = state.get("cartItems", [])
    return isinstance(cart_items, list) and len(cart_items) == 0


if __name__ == "__main__":
    print(validate_task_four())
