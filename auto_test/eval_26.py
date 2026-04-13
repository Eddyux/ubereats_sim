TARGET_VALUE = "32.17"


def validate_task_twenty_six(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False
    normalized_message = final_message.lower()

    if "final_message" in result and (
        "32.17" in final_message or
        "\u4e09\u5341\u4e8c\u70b9\u4e00\u4e03" in final_message or
        "thirty-two point one seven" in normalized_message
    ):
        return True
    else:
        return False


if __name__ == "__main__":
    print(validate_task_twenty_six())
