def validate_task_seventeen(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False
    normalized_message = final_message.lower()

    if "final_message" in result and (
        "116.24" in final_message or
        "\u4e00\u767e\u4e00\u5341\u516d\u70b9\u4e8c\u56db" in final_message or
        "one hundred twelve point two four" in normalized_message
    ):
        return True
    else:
        return False


if __name__ == "__main__":
    print(validate_task_seventeen())
