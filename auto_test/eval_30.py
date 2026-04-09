from appsim.utils import read_json_from_device

PACKAGE_NAME = "com.example.ubereats_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "send_message"
PAGE_VALUE = "sendmessages"
MESSAGE_VALUE = "How long will it take to arrive?"


def validate_task_thirty(result=None, device_id=None, backup_dir=None):
    try:
        all_data = read_json_from_device(device_id, PACKAGE_NAME, DEVICE_FILE_PATH, backup_dir)
        events = all_data if isinstance(all_data, list) else [all_data]
    except Exception:
        return False

    for event in reversed(events):
        if event.get("action") != ACTION_VALUE or event.get("page") != PAGE_VALUE:
            continue
        extra_data = event.get("extra_data", {})
        if str(extra_data.get("message", "")).strip() == MESSAGE_VALUE:
            return True
    return False


if __name__ == "__main__":
    print(validate_task_thirty())
