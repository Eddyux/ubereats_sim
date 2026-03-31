def validate_task_fifteen(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower()
    accepted_counts = {"2", "3", "two", "three"}
    has_count = any(token in normalized for token in accepted_counts)
    has_burger = "汉堡" in final_message or "burger" in normalized
    return has_count and has_burger


if __name__ == "__main__":
    print(validate_task_fifteen())
