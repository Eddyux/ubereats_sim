def validate_task_sixteen(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower()
    accepted_time_tokens = ["30 min", "30分钟", "30 分钟", "9:45 pm", "45 min"]
    has_eta_context = any(
        token in normalized for token in ["送达", "arrival", "eta", "还要", "arrive"]
    )
    return has_eta_context and any(token in normalized or token in final_message for token in accepted_time_tokens)


if __name__ == "__main__":
    print(validate_task_sixteen())
