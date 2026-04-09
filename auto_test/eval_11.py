def validate_task_eleven(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    if '20.19' in final_message:
        return True
    else:
        return False


if __name__ == "__main__":
    print(validate_task_eleven())
