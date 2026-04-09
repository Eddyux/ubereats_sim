TARGET_VALUE = "18.99"


def validate_task_twenty_seven(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    return TARGET_VALUE in final_message


if __name__ == "__main__":
    print(validate_task_twenty_seven())
