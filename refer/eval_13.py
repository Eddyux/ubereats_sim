def validate_task_thirteen(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower()
    has_count = "7" in final_message or "seven" in normalized
    has_free_delivery = any(
        token in normalized for token in ["free delivery", "免配送费", "delivery fee"]
    )
    return has_count and has_free_delivery


if __name__ == "__main__":
    print(validate_task_thirteen())
