all:

front:
	cd frontend && npm run dev

back:
	cd backend && ./gradlew bootRun --args='--spring.profiles.active=local'

pre-push: front-check back-check

front-check:
	cd frontend && npm install
	cd frontend && npm run lint
	cd frontend && npm run format
	cd frontend && npm run test
	cd frontend && npm run build

back-check:
	cd backend && ./gradlew ktlintFormat
	cd backend && ./gradlew ktlintCheck
	cd backend && ./gradlew test
	cd backend && ./gradlew build -x test

# フロントエンドのパッケージをアップデート
fe-up:
	cd frontend && ncu -i

# バックエンドのパッケージをアップデート
be-up:
	cd backend && ./gradlew dependencyUpdates