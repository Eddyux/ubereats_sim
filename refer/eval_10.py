def validate_task_ten(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    message = final_message.lower()
    has_item = "hash browns" in message or "薯饼" in final_message
    has_price = "$4.59" in final_message or "4.59" in final_message
    has_merchant = "麦当劳" in final_message or "mcdonald" in message
    return has_item and has_price and has_merchant


if __name__ == "__main__":
    print(validate_task_ten())
