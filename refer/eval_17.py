def validate_task_seventeen(result=None, device_id=None, backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False

    normalized = final_message.lower().replace(",", "")
    accepted_prices = ["17.98", "28.50", "28.5"]
    has_week_context = "上周" in final_message or "last week" in normalized
    has_burger = "汉堡" in final_message or "burger" in normalized
    return has_week_context and has_burger and any(price in normalized for price in accepted_prices)


if __name__ == "__main__":
    print(validate_task_seventeen())
