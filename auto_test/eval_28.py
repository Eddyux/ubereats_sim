TARGET_VALUE = "26.49"


def validate_task_twenty_eight(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False
    normalized_message = final_message.lower()

    if "final_message" in result and (
        "26.49" in final_message or
        "\u4e8c\u5341\u516d\u70b9\u56db\u4e5d" in final_message or
        "twenty-six point four nine" in normalized_message
    ):
        return True
    else:
        return False


if __name__ == "__main__":
    print(validate_task_twenty_eight())
