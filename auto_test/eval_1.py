def validate_task_one(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False

    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    message = final_message.strip().lower()
    has_merchant = "mcdonald" in message or "麦当劳" in final_message
    has_yes = "yes" in message or "有" in final_message
    return has_merchant and has_yes


if __name__ == "__main__":
    print(validate_task_one())
