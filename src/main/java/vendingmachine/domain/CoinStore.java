package vendingmachine.domain;

import static vendingmachine.utils.Constant.*;

import java.util.EnumMap;

import camp.nextstep.edu.missionutils.Randoms;

public class CoinStore {
	private final EnumMap<Coin, Integer> coins = new EnumMap<Coin, Integer>(Coin.class) {
		{
			put(Coin.findCoin(500), 0);
			put(Coin.findCoin(100), 0);
			put(Coin.findCoin(50), 0);
			put(Coin.findCoin(10), 0);
		}
	};
	private final EnumMap<Coin, Integer> changeCoins = new EnumMap<>(coins);

	public CoinStore(int money) {
		createRandomCoins(money);
	}

	private void createRandomCoins(int money) {
		while (Coin.checkIsAtLeastCoin(money)) {
			int randomCoin = findRandomCoin();
			if (randomCoin <= money) {
				money -= randomCoin;
				plusCoin(randomCoin);
			}
		}
	}

	private void plusCoin(int money) {
		Coin findCoin = Coin.findCoin(money);
		coins.put(findCoin, coins.get(findCoin) + PLUS_COIN_ELEMENT);
	}

	private int findRandomCoin() {
		return Randoms.pickNumberInList(Coin.findCoinListByInteger());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int amountOfCoin: Coin.getAmountListOfCoin()) {
			stringBuilder.append(amountOfCoin).append("원");
			stringBuilder.append(DELIMITER_OF_COIN_STORE_STATUS_REPRESENT);
			stringBuilder.append(coins.get(Coin.findCoin(amountOfCoin))).append("개");
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	private void makeChangeCoins(int amount) {
		while (Coin.checkIsAtLeastCoin(amount)) {
			Coin biggestChangeCoin = Coin.findBiggestChangeCoin(amount);
			amount -= biggestChangeCoin.getAmount();
			changeCoins.put(biggestChangeCoin, changeCoins.get(biggestChangeCoin) + 1);
		}
	}

	public String changeCoinsToString(int amount) {
		makeChangeCoins(amount);
		StringBuilder stringBuilder = new StringBuilder();
		for (Coin amountOfCoin: changeCoins.keySet()) {
			if (changeCoins.get(amountOfCoin) > 0) {
				stringBuilder.append(amountOfCoin.getAmount()).append("원");
				stringBuilder.append(DELIMITER_OF_COIN_STORE_STATUS_REPRESENT);
				stringBuilder.append(changeCoins.get(amountOfCoin)).append("개");
				stringBuilder.append("\n");
			}
		}
		return stringBuilder.toString();
	}
}
