front:
	cd frontend && npm run dev

back:
	cd backend && ./gradlew bootRun --args='--spring.profiles.active=local'

pre-push:
	cd frontend && npm install
	cd frontend && npm run lint
	cd frontend && npm run format
	cd frontend && npm run test
	cd frontend && npm run build
	cd backend && ./gradlew build -x test
	cd backend && ./gradlew test
