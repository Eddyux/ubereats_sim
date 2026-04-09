def validate_task_seven(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    if '有' in final_message:
        return True
    else:
        return False


if __name__ == "__main__":
    print(validate_task_seven())
