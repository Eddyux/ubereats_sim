def validate_task_fourteen(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower().replace(",", "")
    has_wallet = "wallet" in normalized or "uber cash" in normalized or "账户" in final_message
    has_zero = any(token in final_message for token in ["0", "0.00", "CN¥0.00", "¥0.00"])
    return has_wallet and has_zero


if __name__ == "__main__":
    print(validate_task_fourteen())
