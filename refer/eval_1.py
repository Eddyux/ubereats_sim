def validate_task_one(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    message = final_message.lower()
    has_merchant = "麦当劳" in final_message or "mcdonald" in message
    has_nearby = "附近" in final_message or "nearby" in message
    has_match = any(token in final_message for token in ["有", "找到", "可以"]) or any(
        token in message for token in ["found", "available", "yes"]
    )
    return has_merchant and (has_nearby or has_match)


if __name__ == "__main__":
    print(validate_task_one())
