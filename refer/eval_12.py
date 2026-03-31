def validate_task_twelve(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower().replace(",", "")
    accepted_prices = ["229.72", "201.22"]
    has_date = "3.29" in final_message or "march 29" in normalized or "03-29" in normalized
    return has_date and any(price in normalized for price in accepted_prices)


if __name__ == "__main__":
    print(validate_task_twelve())
