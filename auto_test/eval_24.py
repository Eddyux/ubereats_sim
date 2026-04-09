TARGET_VALUE = "Burger"


def validate_task_twenty_four(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    if "Burger" in result["final_message"]:
        return True
    else:
        return False


if __name__ == "__main__":
    print(validate_task_twenty_four())
