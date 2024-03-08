## Git subtree
Git subtree는 Git 저장소에서 다른 저장소를 포함하고 하나의 디렉토리로 관리할 수 있는 기능입니다. 
이는 하나의 저장소에서 다른 저장소의 특정 디렉토리를 포함하여 병합할 때 유용합니다. 
Git subtree를 사용하면 다른 저장소를 서브 디렉토리로 추가하고 관리할 수 있습니다.

#### 기존 저장소에 여러 브랜치가 있는 경우 다음 Git 명령어를 사용하여 Git subtree로 이동할 수 있습니다:

1. 새로운 브랜치 생성:
```
git checkout -b subtree-branch
```
2. 새로운 브랜치에 Git subtree 추가:
```
git subtree add --prefix=<새로운 디렉토리 경로> <원격 저장소 URL> <브랜치명>
```
예를 들어:
```
git subtree add --prefix=subtree-directory https://github.com/example-repo.git main
```
3. 필요에 따라 원본 저장소의 변경사항을 가져오기:
```
git subtree pull --prefix=<새로운 디렉토리 경로> <원격 저장소 URL> <브랜치명>
```
예를 들어:
```
git subtree pull --prefix=subtree-directory https://github.com/example-repo.git main
```
이렇게 하면 기존 저장소의 여러 브랜치를 가진 상태에서 Git subtree로 이동할 수 있습니다.
