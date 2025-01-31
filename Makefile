front:
	cd frontend && npm run dev

pre-push:
	cd frontend && npm install
	cd frontend && npm run lint
	cd frontend && npm run format
	cd frontend && npm run test
	cd frontend && npm run build