build:
	mvn clean package

upload:
	gcloud functions deploy quarkus-demo-http \
	--entry-point=io.quarkus.gcp.functions.http.QuarkusHttpFunction \
	--runtime=java11 --trigger-http --source=target/deployment \
	--region=europe-west2

deploy: build upload