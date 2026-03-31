def validate_task_eleven(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower().replace(",", "")
    accepted_prices = ["18.56", "32.98", "35.90", "35.9", "35.91"]
    return any(price in normalized for price in accepted_prices)


if __name__ == "__main__":
    print(validate_task_eleven())
